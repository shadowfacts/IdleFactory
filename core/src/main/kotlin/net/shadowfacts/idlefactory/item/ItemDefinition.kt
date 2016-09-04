package net.shadowfacts.idlefactory.item

import com.badlogic.gdx.graphics.Texture

/**
 * @author shadowfacts
 */
abstract class ItemDefinition(val name: String, val texture: Texture) {

	abstract fun register()

}