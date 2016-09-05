package net.shadowfacts.idlefactory.world

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import net.shadowfacts.idlefactory.nbt.NBTSerializable
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.nbt.impl.TagList
import net.shadowfacts.idlefactory.scene.GameScene
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.Tickable
import net.shadowfacts.idlefactory.tile.Tile
import net.shadowfacts.idlefactory.tile.impl.TileFloor
import net.shadowfacts.idlefactory.tile.impl.TileGrass
import net.shadowfacts.idlefactory.tile.impl.TileWall
import java.util.*

/**
 * @author shadowfacts
 */
class World : Tickable, NBTSerializable<TagCompound> {

	val tiles: Array<Array<Tile>>

	constructor() {
		val list: MutableList<Array<Tile>> = mutableListOf()
		for (x in 0.until(64)) {
			val innerList: MutableList<Tile> = mutableListOf()
			for (y in 0.until(64)) {
				innerList.add(TileFloor(this, Pos(x, y)))
			}
			list.add(innerList.toTypedArray())
		}
		tiles = list.toTypedArray()
	}

	constructor(tag: TagCompound) {
		val tagList = tag.getTagList("x") as List<TagList>
		val list = ArrayList<Array<Tile>>(tagList.size)
		for (x in 0.until(tagList.size)) {
			val innerTagList = tagList[x]
			val innerList = ArrayList<Tile>(innerTagList.size)
			for (y in 0.until(innerTagList.size)) {
				innerList.add(Tile.deserializeNBT(innerTagList[y] as TagCompound, this))
			}
			list.add(innerList.toTypedArray())
		}
		tiles = list.toTypedArray()
	}

	constructor(input: String) {
		val lines = input.split("\n").reversed()

		val list = ArrayList<Array<Tile>>()

		for (i in 0.until(lines.size)) {
			val line = lines[i]

			val innerList = ArrayList<Tile>()

			for (j in 0.until(line.length)) {
				innerList.add(getTile(line[j], this, Pos(j, i)))
			}

			list.add(innerList.toTypedArray())
		}

		tiles = list.toTypedArray()
	}

	private fun getTile(c: Char, world: World, pos: Pos): Tile {
		if (c == '-' || c == '|' || c == '+') {
			return TileWall(world, pos, false)
		} else if (c == 'G') {
			return TileGrass(world, pos)
		} else {
			return TileFloor(world, pos)
		}
	}

	fun get(x: Int, y: Int): Tile {
		return tiles[y][x]
	}

	fun get(pos: Pos): Tile = get(pos.x, pos.y)

	fun set(x: Int, y: Int, tile: Tile) {
		val old = tiles[y][x]
		tiles[y][x] = tile

		if (x > 0) {
			get(x - 1, y).onNeighborChanged(tile, old)
		}
		if (x < getXSize() - 1) {
			get(x + 1, y).onNeighborChanged(tile, old)
		}
		if (y > 0) {
			get(x, y - 1).onNeighborChanged(tile, old)
		}
		if (y < getYSize() - 1) {
			get(x, y + 1).onNeighborChanged(tile, old)
		}
	}

	fun set(pos: Pos, tile: Tile) = set(pos.x, pos.y, tile)

	fun getXSize(): Int {
		return tiles[0].size
	}

	fun getYSize(): Int {
		return tiles.size
	}

	fun isWithinBorder(x: Int, y: Int): Boolean {
		return x >= 0 && x < getXSize() &&
				y >= 0 && y < getYSize()
	}

	fun isWithinBorder(pos: Pos): Boolean = isWithinBorder(pos.x, pos.y)

	fun getPosAtScreenCoords(screenX: Int, screenY: Int): Pos {
		val unprojected = GameScene.camera!!.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
		val x = Math.floor(unprojected.x / 21.0).toInt()
		val y = Math.floor(unprojected.y / 21.0).toInt()
		return Pos(x, y)
	}

	override fun tick() {
		tiles.forEach {
			it.forEach {
				it.tickComponents()
				if (it is Tickable) {
					it.tick()
				}
			}
		}
	}

	fun draw(batch: SpriteBatch) {
		tiles.forEach {
			it.forEach {
				it.draw(batch)
			}
		}
	}

	override fun serializeNBT(tag: TagCompound): TagCompound {
		val list = TagList("x")
		for (y in 0.until(getYSize())) {
			val innerList = TagList("y")
			for (x in 0.until(getXSize())) {
				innerList.add(get(x, y).serializeNBT(TagCompound("tile")))
			}
			list.add(innerList)
		}
		tag["x"] = list
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {

	}

}
