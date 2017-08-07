import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class FbbRemoval extends AnAction
{

    public void actionPerformed(AnActionEvent event) {


        Project project = event.getData(PlatformDataKeys.PROJECT);


        String fbName= Messages.showInputDialog(project, "What is your FB name?", "FB name input", IconLoader.getIcon("/icon.png"));
        try
        {

            FileScanner fileScanner = new FileScanner(
                    project.getBasePath(),
                    fbName);
            fileScanner.scan();


        }
        catch (Exception ex)
        {
            Messages.showErrorDialog("Bummer, something bad happened:\n" + ex.getMessage(), "Error!");

        }

        Messages.showMessageDialog(project, "Done, check console output for more details", "Done" , IconLoader.getIcon("/icon.png"));
    }
}