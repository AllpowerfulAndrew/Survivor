/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package view

import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.layout.StackPane
import javafx.util.Duration
import java.util.*

class ScreensController : StackPane() {
    //Holds the screens to be displayed

    private val screens = HashMap<String, Node>()

    //Add the screen to the collection
    fun addScreen(name: String, screen: Node) {
        screens[name] = screen
    }

    //Returns the Node with the appropriate name
    fun getScreen(name: String): Node {
        return screens[name]!!
    }

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    fun loadScreen(name: String, resource: String) = try {
        val myLoader = FXMLLoader(javaClass.getResource(resource))
        val loadScreen = myLoader.load<Parent>()
        val myScreenController = myLoader.getController<ControlledScreen>()
        myScreenController.setScreenParent(this)
        addScreen(name, loadScreen)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        println(e.message)
        false
    }

    //This method tries to displayed the screen with a predefined name.
    //First it makes sure the screen has been already loaded.  Then if there is more than
    //one screen the new screen is been added second, and then the current screen is removed.
    // If there isn't any screen being displayed, the new screen is just added to the root.
    fun setScreen(name: String): Boolean {
        if (screens[name] != null) {   //screen loaded
            val opacity = opacityProperty()

            if (!children.isEmpty()) {    //if there is more than one screen
                val fade = Timeline(
                        KeyFrame(Duration.ZERO, KeyValue(opacity, 1.0)),
                        KeyFrame(Duration(100.0), EventHandler<ActionEvent> {
                            children.removeAt(0)                    //remove the displayed screen
                            children.add(0, screens[name])     //add the screen
                            val fadeIn = Timeline(
                                    KeyFrame(Duration.ZERO, KeyValue(opacity, 0.0)),
                                    KeyFrame(Duration(100.0), KeyValue(opacity, 1.0)))
                            fadeIn.play()
                        }, KeyValue(opacity, 0.0)))
                fade.play()

            } else {
                setOpacity(0.0)
                children.add(screens[name])       //no one else been displayed, then just show
                val fadeIn = Timeline(
                        KeyFrame(Duration.ZERO, KeyValue(opacity, 0.0)),
                        KeyFrame(Duration(100.0), KeyValue(opacity, 1.0)))
                fadeIn.play()
            }
            return true
        } else {
            println("screen hasn't been loaded!!! \n")
            return false
        }


        /*Node screenToRemove;
         if(screens.get(name) != null){   //screen loaded
         if(!getChildren().isEmpty()){    //if there is more than one screen
         getChildren().add(0, screens.get(name));     //add the screen
         screenToRemove = getChildren().get(1);
         getChildren().remove(1);                    //remove the displayed screen
         }else{
         getChildren().add(screens.get(name));       //no one else been displayed, then just show
         }
         return true;
         }else {
         System.out.println("screen hasn't been loaded!!! \n");
         return false;
         }*/
    }

    //This method will remove the screen with the given name from the collection of screens
    fun unloadScreen(name: String) = if (screens.remove(name) == null) {
        println("Screen didn't exist")
        false
    } else {
        true
    }
}
