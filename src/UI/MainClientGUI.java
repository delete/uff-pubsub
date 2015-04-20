package UI;

import client.ClientControl;
import model.Article;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by regmoraes on 19/04/15.
 */
public class MainClientGUI extends JFrame{
    private JList listArticles;
    private JPanel rootPanel;
    private JComboBox comboBoxCategorys;
    private JButton buttonSubscribe;
    private JTextField textFieldArticleCategory;
    private JTextArea textAreaArticleContent;
    private JTextField textFieldArticleTitle;
    private JButton buttonPublish;
    private JLabel labelCurrentSubscriptions;
    private JList listSubscriptions;
    private JLabel labelCategory;
    private ClientControl control;
    List<Article> myArticles = new ArrayList<>();
    List<String> mySubscriptions = new ArrayList<>();
    DefaultListModel listModelSubscriptions;
    DefaultListModel listModelArticles;


    public MainClientGUI(ClientControl c){

        this.control = c;
    }

    public void showSubscribePanel() throws RemoteException {

        setContentPane(rootPanel);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addListeners();
        updateSubscriptionsCategory();
    }


    private void addListeners(){

        buttonPublish.addActionListener(actionListener -> {

            if (!textFieldArticleCategory.getText().equals("") &&
                    !textFieldArticleCategory.getText().equals("") &&
                    !textAreaArticleContent.getText().equals("")) {

                try {
                    publish();
                    updateSubscriptionsCategory();

                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            } else {

                JOptionPane.showMessageDialog(null, "Article fields must be filled");
            }
        });

        buttonSubscribe.addActionListener(actionListener -> {

            try {

                subscribe();
                updateMySubscriptions((String) comboBoxCategorys.getSelectedItem());

            } catch (RemoteException e) {
                e.printStackTrace();
            }

        });

    }

    public void updateMySubscriptions(String keyword) throws RemoteException {

        mySubscriptions.add(keyword);

        listModelSubscriptions = new DefaultListModel<String>();

        mySubscriptions.forEach(listModelSubscriptions :: addElement);
        listSubscriptions.setModel(listModelSubscriptions);
    }

    public void updateSubscriptionsCategory() throws RemoteException {

        List<String> list = control.contactServer().getSubscriptionsCategory();

        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();

        list.forEach(comboBoxModel::addElement);

        comboBoxCategorys.setModel(comboBoxModel);
    }

    public void subscribe() throws RemoteException {

        control.contactServer().subscribe(control.getClient(), (String) comboBoxCategorys.getSelectedItem());
    }


    public void publish() throws RemoteException {

        control.contactServer().publish(new Article(textFieldArticleCategory.getText(),
                textFieldArticleTitle.getText(), textAreaArticleContent.getText()));
    }

    public void notifyNewPublication(Article a){

        System.out.println("FOI PESSOAL");
        myArticles.add(a);
        updateArticlesList();
        //JOptionPane.showMessageDialog(null, "The category have a new publication");

    }

    public void updateArticlesList(){

        listModelArticles = new DefaultListModel<String>();

        for(Article a : myArticles){

            String rowInfo = "["+a.getKeyword().toUpperCase()+"] "+a.getTitle();

            listModelArticles.addElement(rowInfo);
        }

        listArticles.setModel(listModelArticles);
    }
}
