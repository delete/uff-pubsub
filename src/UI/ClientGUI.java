package UI;

import model.Article;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by regmoraes on 19/04/15.
 */
public class ClientGUI extends JFrame{

    private JList listArticles;
    private JPanel rootPanel;
    private JButton buttonSubscribe;
    private JTextField textFieldArticleCategory;
    private JTextArea textAreaArticleContent;
    private JTextField textFieldArticleTitle;
    private JButton buttonPublish;
    private JLabel labelCurrentSubscriptions;
    private JList listSubscriptions;
    private JLabel labelCategory;
    private JComboBox comboBoxCategories;

    public static ClientGUI instance;
    List<Article> myArticles = new ArrayList<>();
    List<String> mySubscriptions = new ArrayList<>();
    DefaultListModel listModelSubscriptions;
    DefaultListModel listModelArticles;
    MainControl mainControl = MainControl.getInstance();

    public static ClientGUI getInstance(){

        if(instance == null){
            return new ClientGUI();

        }else {
            return instance;
        }
    }

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

                    Article a = new Article(textFieldArticleCategory.getText(), textFieldArticleTitle.getText(),
                            textAreaArticleContent.getText());

                    mainControl.publish(a);
                    updateSubscriptionsCategory();

                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException f) {
                    f.printStackTrace();
                }
            } else {

                mainControl.showErrorMessage("Article fields must be filled");
            }
        });

        buttonSubscribe.addActionListener(actionListener -> {

            try {

                mainControl.subscribe((String) comboBoxCategories.getSelectedItem());
                updateMySubscriptions((String) comboBoxCategories.getSelectedItem());

            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        });
    }

    private void updateMySubscriptions(String keyword) throws RemoteException {

        mySubscriptions.add(keyword);
        listModelSubscriptions = new DefaultListModel<String>();
        mySubscriptions.forEach(listModelSubscriptions::addElement);
        listSubscriptions.setModel(listModelSubscriptions);
    }

    private void updateSubscriptionsCategory() throws NotBoundException,RemoteException {

        List<String> list = mainControl.getSubscriptionsCategory();

        if(list != null){

            DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();

            list.forEach(comboBoxModel::addElement);

            comboBoxCategories.setModel(comboBoxModel);
        }
    }

    public void notifyNewPublication(Article a){

        myArticles.add(a);
        listModelArticles = new DefaultListModel<String>();

        for(Article article : myArticles){

            String rowInfo = "["+article.getKeyword().toUpperCase()+"] "+article.getTitle();

            listModelArticles.addElement(rowInfo);
        }

        listArticles.setModel(listModelArticles);
        System.out.println("Article received");


    }
}
