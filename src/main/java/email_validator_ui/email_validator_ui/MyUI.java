package email_validator_ui.email_validator_ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.AbstractErrorMessage.ContentMode;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	//tells user the basics of what they have to do
        final VerticalLayout layout = new VerticalLayout();
        final Label emailprompt = new Label();
        emailprompt.setCaption("Please enter your email address below to confirm its validty:");
        final Label warning = new Label();
        warning.setCaption("Note: Ensure that your address contains one '@' sign, at least one '.', all lower case, and no swears from the list below.");
        final Label swears = new Label();
        swears.setCaption("Swears: mudblood, dolphinsound, cottonheadedninnymuggins, nerfherder, or prawn");

        //text field and buttion
        final TextField emailField = new TextField("Enter email here");
        Button button = new Button("Press to validate");
        Label assessment = new Label("");
        
        //checks to see when button is pressed, and when it is it takes the email and
        //lets the user know if the user name is okay, or if there is something wrong with it
        //and what is wrong with it.
        button.addClickListener( e -> {
            EmailChecker emailTest = new EmailChecker();
            String email = emailField.getValue();
            int passes = emailTest.validate(email);
            if(passes==4)
            	assessment.setCaption("Congratulations, your email address is valid.");
            else{
            	String error = " error ";
            	if(passes!=3)
            		error=" errors ";
            	String errorMessage = ("Your email contains "+(4-passes)+error+": "); 
            	if(!emailTest.containsOneAt(email))
            		errorMessage += "Your email does not contain one '@' symbol. ";
            	if(!emailTest.containsAllLowerCase(email))
            		errorMessage += "Your email does not contain all lowercase. ";
            	if(!emailTest.containsNoSwears(email))
            		errorMessage += "Your email contains a swear. ";
            	if(!emailTest.containsPeriod(email))
            		errorMessage += "Your email does not contain a period. ";
            	assessment.setCaption(errorMessage);
            		
            }
            	
        });
        
        layout.addComponents(emailprompt, warning, swears, emailField, button, assessment);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
