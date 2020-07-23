package com.github.javaru.balloondemo.actions


import com.github.javaru.balloondemo.services.MyProjectService
import com.intellij.icons.AllIcons
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.impl.NotificationsManagerImpl
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.BalloonLayoutData
import com.intellij.ui.awt.RelativePoint
import org.intellij.lang.annotations.Language
import java.awt.Point


class ShowLargeBalloonAction : AbstractShowBalloonAction()
{
    @Language("HTML")
    override fun getContent(): String =
            """
               
<html>
<h2>Lorem ipsum</h2>
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et 
dolore magna aliqua. Tempus iaculis urna id volutpat lacus laoreet non curabitur. Leo vel orci porta non 
pulvinar. Ullamcorper a lacus vestibulum sed arcu non odio euismod. Turpis nunc eget lorem dolor sed viverra 
ipsum nunc. Sed sed risus pretium quam. Sed libero enim sed faucibus turpis in. Facilisis magna etiam 
tempor orci. Ullamcorper sit amet risus nullam eget felis eget. Sit amet nulla facilisi morbi tempus. 
Ipsum suspendisse ultrices gravida dictum fusce ut.
<br/><br/>
Ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at augue. Neque vitae tempus quam pellentesque.
Ipsum faucibus vitae aliquet nec ullamcorper sit. Scelerisque viverra mauris in aliquam. Neque sodales ut 
etiam sit. Ac tincidunt vitae semper quis. Phasellus faucibus scelerisque eleifend donec pretium vulputate 
sapien nec. Fermentum odio eu feugiat pretium nibh ipsum. Aliquam faucibus purus in massa tempor nec feugiat 
nisl. Tempus quam pellentesque nec nam aliquam sem et tortor. Tincidunt lobortis feugiat vivamus at. Vitae 
tempus quam pellentesque nec nam. Mi tempus imperdiet nulla malesuada pellentesque elit eget gravida. Netus 
et malesuada fames ac turpis egestas sed tempus urna. Consectetur adipiscing elit pellentesque habitant. 
Commodo odio aenean sed adipiscing diam. Condimentum id venenatis a condimentum vitae. Ac tincidunt vitae 
semper quis lectus nulla. Nibh tortor id aliquet lectus proin nibh nisl. Nam aliquam sem et tortor consequat.
</html>
            """.trimIndent()

}

class ShowSmallBalloonAction : AbstractShowBalloonAction()
{
    @Language("HTML")
    override fun getContent(): String = """<html>An example balloon.</html>"""
}

abstract class AbstractShowBalloonAction : DumbAwareAction()
{
    override fun actionPerformed(e: AnActionEvent)
    {
        val project = e.getData(CommonDataKeys.PROJECT)


        val notification = Notification("MyNotifications",
                                        AllIcons.General.BalloonInformation,
                                        "Test Balloon",
                                        null,
                                        getContent(),
                                        NotificationType.INFORMATION,
                                        null
                                       )

        showBalloon(notification, project)
    }

    @Language("HTML")
    abstract fun getContent(): String
}

class MyActionGroup : DefaultActionGroup()

fun showBalloon(notification: Notification, project: Project?, hideOnClickOutside: Boolean = false)
{
    val frame = WindowManager.getInstance().getIdeFrame(project)
    if (frame == null || project == null)
    {
        notification.notify(project)
    }
    else
    {
        val bounds = frame.component.bounds
        val target = RelativePoint(frame.component, Point(bounds.x + bounds.width, 20))
        val balloon = NotificationsManagerImpl.createBalloon(frame,
                                                             notification,
                                                             true,
                                                             hideOnClickOutside,
                                                             BalloonLayoutData.fullContent(),
                                                             project.service<MyProjectService>())
        balloon.show(target, Balloon.Position.atLeft)
    }
}