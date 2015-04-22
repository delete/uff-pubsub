package ui.client;

import client.IClient;
import main.MainPubSub;
import server.Article;
import server.IServer;
import ui.IGUI;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by regmoraes on 19/04/15.
 */
public class ClientGUI extends JFrame implements IGUI,IClientGUI{

    private JList listArticles;
    private JPanel rootPanel;
    private JButton buttonSubscribe;
    private JTextField textFieldArticleCategory;
    private JTextArea textAreaArticleContent;
    private JTextField textFieldArticleTitle;
    private JButton buttonPublish;
    private JLabel labelCurrentSubscriptions;
    public JList listSubscriptions;
    private JLabel labelCategory;
    private JComboBox comboBoxCategories;

    public MainPubSub mainPubSub = MainPubSub.getInstance();
    private static ClientGUI instance;
    private DefaultListModel<String> listModelSubscriptions = new DefaultListModel<>();
    private DefaultListModel<String> listModelArticles = new DefaultListModel<>();

    public static ClientGUI getInstance(){

        if(instance == null){
            return new ClientGUI();

        }else {
            return instance;
        }
    }

    @Override
    public void initializeGUI(){

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeListeners();

        setVisible(true);
    }

    private void initializeListeners(){

        buttonPublish.addActionListener(actionListener -> {

            if (!textFieldArticleCategory.getText().equals("") &&
                    !textFieldArticleCategory.getText().equals("") &&
                    !textAreaArticleContent.getText().equals("")) {

                try {

                    IServer server = mainPubSub.server;

                    Article a = new Article(textFieldArticleCategory.getText(), textFieldArticleTitle.getText(),
                            textAreaArticleContent.getText());

                    server.publish(a);
                    updateSubscriptionsCategory();

                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
            } else {

                //mainPubSub.showErrorMessage("Article fields must be filled");
            }
        });

        buttonSubscribe.addActionListener(actionListener -> {

            try {

                IServer server = mainPubSub.server;
                IClient client = mainPubSub.client;

                server.subscribe(client, (String) comboBoxCategories.getSelectedItem());
                updateMySubscriptions((String) comboBoxCategories.getSelectedItem());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateMySubscriptions(String keyword){

        listModelSubscriptions.addElement(keyword);
        listSubscriptions.setModel(listModelSubscriptions);
    }

    private void updateSubscriptionsCategory() throws NotBoundException,RemoteException {

        IServer server = mainPubSub.server;

        List<String> list = server.getSubscriptionsCategory();

        if(list != null){

            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

            list.forEach(comboBoxModel::addElement);

            comboBoxCategories.setModel(comboBoxModel);
        }
    }

    @Override
    public void showNewArticles(Article a){

        String infoArticle = ("["+a.getKeyword().toUpperCase()+"] "+a.getTitle());

        listModelArticles.addElement(infoArticle);
        listArticles.setModel(listModelArticles);

        JOptionPane.showMessageDialog(this.getContentPane(), infoArticle);//Apenas para debug
    }
}
