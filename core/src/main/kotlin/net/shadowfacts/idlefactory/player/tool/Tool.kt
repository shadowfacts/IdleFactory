package net.shadowfacts.idlefactory.player.tool

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import net.shadowfacts.idlefactory.nbt.NBTSerializeable
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
abstract class Tool(val texture: Texture) : NBTSerializeable<TagCompound> {

	abstract fun onLeftClick(world: World, screenX: Int, screenY: Int)

	abstract fun onRightClick(world: World, screenX: Int, screenY: Int)

	abstract fun drawOverlay(world: World, batch: SpriteBatch, shapeRenderer: ShapeRenderer)

}