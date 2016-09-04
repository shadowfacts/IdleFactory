package net.shadowfacts.idlefactory.gui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import net.shadowfacts.idlefactory.IdleFactory
import net.shadowfacts.idlefactory.gui.widget.WidgetButton
import net.shadowfacts.idlefactory.gui.widget.WidgetLabel
import net.shadowfacts.idlefactory.gui.widget.WidgetTexture
import net.shadowfacts.idlefactory.scene.GameScene
import net.shadowfacts.idlefactory.util.Textures

/**
 * @author shadowfacts
 */
class GUIMainMenu : GUI() {

	init {
		val layout = GlyphLayout(IdleFactory.font!!, "Start Game")
		val x = (Gdx.graphics.width / 2) - (layout.width / 2)
		val y = (Gdx.graphics.height / 2) - (layout.height / 2)
		val button = WidgetButton(x, y, 100f, 20f, layout)
		button.clickHandler = {
			IdleFactory.setScene(GameScene)
			true
		}
		widgets.add(button)

//		widgets.add(WidgetTexture(0f, 0f, Textures.LOGO))
//		val layout = GlyphLayout(IdleFactory.font, "IdleFactory")
//		widgets.add(WidgetLabel(Gdx.graphics.width - layout.width, Gdx.graphics.height.toFloat(), layout))
	}

}