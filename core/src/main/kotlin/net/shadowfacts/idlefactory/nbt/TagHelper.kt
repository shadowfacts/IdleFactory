package net.shadowfacts.idlefactory.nbt

import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import java.io.*
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

/**
 * @author shadowfacts
 */
object TagHelper {

	fun readFile(path: String, compressed: Boolean = true): TagCompound {
		return readFile(File(path), compressed)
	}

	fun readFile(file: File, compressed: Boolean = true): TagCompound {
		var input: InputStream = FileInputStream(file)

		if (compressed) input = GZIPInputStream(input)

		val tag = readTag(DataInputStream(input))
		input.close()

		if (tag is TagCompound) {
			return tag
		} else {
			throw IOException("Root tag is not a TagCompound")
		}
	}

	fun writeFile(tag: TagCompound, path: String, compressed: Boolean = true) {
		writeFile(tag, File(path), compressed)
	}

	fun writeFile(tag: TagCompound, file: File, compressed: Boolean = true) {
		if (!file.exists()) {
			if (file.parentFile != null && !file.parentFile.exists()) {
				file.parentFile.mkdirs()
			}
			file.createNewFile()
		}

		var output: OutputStream = FileOutputStream(file)

		if (compressed) output = GZIPOutputStream(output)

		writeTag(DataOutputStream(output), tag)
		output.close()
	}

	fun readTag(input: DataInputStream): Tag? {
		val id = input.readUnsignedByte()
		if (id == 0) {
			return null
		}

		val name = input.readUTF()
		val tag = TagRegistry.createInstance(id, name)

		tag.read(input)

		return tag
	}

	fun readTagThrowless(input: DataInputStream): Tag? {
		try {
			return readTag(input)
		} catch (e: IOException) {
			e.printStackTrace()
			return null
		}
	}

	fun writeTag(output: DataOutputStream, tag: Tag) {
		output.writeByte(TagRegistry.getIdFor(tag.javaClass))
		output.writeUTF(tag.name)
		tag.write(output)
	}

	fun writeTagThrowless(output: DataOutputStream, tag: Tag) {
		try {
			writeTag(output, tag)
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}

}