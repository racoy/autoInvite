import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private TextArea accList;

    @FXML
    private TextField firstAccField;

    @FXML
    private TextField invPlanField;

    @FXML
    private CheckBox changeIP;

    @FXML
    private TextArea destGroupList;

    @FXML
    private TextField baseAddresField;

    @FXML
    private Button goInviteBtn;

    @FXML
    private TextField lastNumber;

    @FXML
    void goInvite(ActionEvent event) {
        int invPlan = Integer.parseInt(invPlanField.getText());
        int firstAcc = Integer.parseInt(firstAccField.getText());
        String baseAddres = baseAddresField.getText();
        ArrayList<String> groups = new ArrayList<String>(Arrays.asList(destGroupList.getText().split("\n")));
        StringBuilder accs = new StringBuilder(accList.getText());
        //AccFilter.dropDuplicate(accs);
        //accList.setText(accs.toString());
        for (String group : groups) {
            int lastAcc;
            Inviter inviter = new Inviter();
            if (changeIP.isSelected()) inviter.changeIp(baseAddres + "/?p=tor_proxy");
            lastAcc = inviter.invite(firstAcc, invPlan, baseAddres + "\\?p=inviter", group, accs);
            lastNumber.setText(Integer.toString(lastAcc));
            firstAccField.setText(Integer.toString(lastAcc));
            firstAcc = lastAcc;
            accList.setText(accs.toString());
        }

    }

}
