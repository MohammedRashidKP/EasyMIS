package easymis.utils;

import easymis.models.entity.TransactionStatus;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author RashidKP
 */
public class AlertHelper {
    public static void showErrorMessage(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
        alert.setHeaderText("");
            alert.show();
    }
    
    public static void showSuccessMessage(String message){
        if(message == null)
            message = "Operation Completed Successfully";
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText("");
            alert.show();
    }
    
    public static void showMessage(TransactionStatus status){
        if(status != null){
            if(status.isSuccess()){
                showSuccessMessage(status.getMessage());
            }else{
                if(status.getValidationErrors() != null && !status.getValidationErrors().isEmpty()){
                    showValidationErrors(status.getValidationErrors());
                }else{
                    showErrorMessage(status.getMessage());
                }
            }
        }
    }

    private static void showValidationErrors(List<ValidationError> validationErrors) {
        StringBuilder builder = new StringBuilder();
        validationErrors.stream().forEach((error) -> {
            builder.append(error.getErrorMessage()).append("\n");
        });
        showErrorMessage(builder.toString());
    }
}
