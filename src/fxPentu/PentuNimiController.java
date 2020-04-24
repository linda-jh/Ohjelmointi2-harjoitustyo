package fxPentu;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Avaa dialogin ohjelman auetessa ja kysyy käyttäjältä rekisterin nimen.
 * @author Linda
 * ljhovila@student.jyu.fi
 * @version 7.2.2020
 *
 */
public class PentuNimiController implements ModalControllerInterface<String> {

        @FXML private TextField textNimi;
        private String nimi = null;

        @FXML void handleKumoaNimi() {
            ModalController.closeStage(textNimi);
        }

        @FXML void handleOKNimi() {
            nimi = textNimi.getText();
            ModalController.closeStage(textNimi);
        }
        
        
        
        @Override
        public String getResult() {
            return nimi;
        }
        
        
        @Override
        public void setDefault(String oletus) {
            textNimi.setText(oletus);
        }
        
        
        /**
         * Mitä tehdään kun dialogi on näytetty
         */
        @Override
        public void handleShown() {
            textNimi.requestFocus();
        }
        
        
        /**
         * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
         * @param modalityStage mille ollaan modaalisia, null = sovellukselle
         * @param oletus mitä nimeä näytetään oletuksena
         * @return null jos painetaan Cancel, muuten kirjoitettu nimi
         */
         public static String kysyNimi(Stage modalityStage, String oletus) {
             return ModalController.showModal(
                     PentuNimiController.class.getResource("PentuNimiView.fxml"),
                     "Penturekisteri", modalityStage, oletus);
         }

    }
