package net.shadowfacts.idlefactory.tile

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.shadowfacts.idlefactory.tile.factory.TileFactory
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.draw
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
abstract class TileSingleTexture(factory: TileFactory, world: World, pos: Pos, val texture: Texture, rotation: Float = 0f) : Tile(factory, world, pos, rotation) {

	override fun draw(batch: SpriteBatch) {
		batch.draw(texture, pos.renderX, pos.renderY, rotation)
	}

}