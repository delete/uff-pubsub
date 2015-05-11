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

    private JPanel rootPanel;
    private JList<String> listArticles;
    private JButton buttonSubscribe;
    private JTextField textFieldArticleCategory;
    private JTextArea textAreaArticleContent;
    private JTextField textFieldArticleTitle;
    private JButton buttonPublish;
    private JLabel labelCurrentSubscriptions;
    public JList<String> listSubscriptions;
    private JLabel labelCategory;
    private JComboBox<String> comboBoxCategories;

    private static ClientGUI instance;
    private String connectedServerIP;
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
    public void initializeGUI() throws NotBoundException, RemoteException {

        try {
            
            connectedServerIP = MainPubSub.getClient().getConnectedServerIP();
        
        } catch (RemoteException e) {
            
            e.printStackTrace();
        }

        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        initializeListeners();
        try {
            updateSubscriptionsCategory();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    private void initializeListeners(){

        buttonPublish.addActionListener(actionListener -> {

            if (!textFieldArticleCategory.getText().equals("") &&
                    !textFieldArticleCategory.getText().equals("") &&
                    !textAreaArticleContent.getText().equals("")) {

                try {

                    IServer server = MainPubSub.getServer(connectedServerIP);

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

                JOptionPane.showMessageDialog(this.getContentPane(), "Article fields must be filled");
            }
        });

        buttonSubscribe.addActionListener(actionListener -> {

            try {

                IServer server = MainPubSub.getServer(connectedServerIP);
                IClient client = MainPubSub.getClient();

                server.subscribe(client, (String) comboBoxCategories.getSelectedItem());
                updateMySubscriptions((String) comboBoxCategories.getSelectedItem());

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {

            }
        });

        listSubscriptions.addListSelectionListener(selectionListener -> {

            if (!selectionListener.getValueIsAdjusting()) {

                try {

                    showArticles(MainPubSub.getClient().getArticles(listSubscriptions.getSelectedValue()));

                } catch (RemoteException e) {

                    e.printStackTrace();

                } catch (NotBoundException e) {

                    e.printStackTrace();
                }
            }
        });
    }

    public void updateMySubscriptions(String keyword){

        listModelSubscriptions.addElement(keyword);
        listSubscriptions.setModel(listModelSubscriptions);
    }

    @Override
    public void notifyNewCategory() throws NotBoundException, RemoteException {

        try {
            
            updateSubscriptionsCategory();
        
        } catch (NotBoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateSubscriptionsCategory() throws NotBoundException,RemoteException {

        IServer server = MainPubSub.getServer(connectedServerIP);

        List<String> list = server.getSubscriptionsCategory();

        if(list != null){

            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

            list.forEach(comboBoxModel::addElement);

            comboBoxCategories.setModel(comboBoxModel);
        }
    }

    @Override
    public void notifyNewArticle(Article a) {

        try {
            showArticles(MainPubSub.getClient().getArticles(a.getKeyword()));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("");
        }

        String articleInfo = ("New Article! \n Category:"+ a.getKeyword().toUpperCase()+"\n Title: "+ a.getTitle());
        System.out.println(articleInfo);
        //JOptionPane.showMessageDialog(this.getContentPane(), articleInfo);
    }

    private void showArticles(List<Article> articles){

        String articleInfo;
        listModelArticles = new DefaultListModel<>();

        for(Article a : articles) {

            articleInfo = ("["+ a.getKeyword().toUpperCase()+"] "+ a.getTitle());
            listModelArticles.addElement(articleInfo);
        }
        listArticles.setModel(listModelArticles);
    }
}