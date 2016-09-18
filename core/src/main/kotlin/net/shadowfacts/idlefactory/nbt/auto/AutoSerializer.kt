package net.shadowfacts.idlefactory.nbt.auto

import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.util.Pos
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.*

/**
 * @author shadowfacts
 */
object AutoSerializer {

	private val serializers: MutableMap<Class<*>, Pair<(TagCompound, String, Any) -> Unit, (TagCompound, String) -> Any>> = HashMap()

	init {
		register(Boolean::class.java, TagCompound::set, TagCompound::getBoolean)
		register(Int::class.java, TagCompound::set, TagCompound::getInt)
		register(Long::class.java, TagCompound::set, TagCompound::getLong)
		register(Double::class.java, TagCompound::set, TagCompound::getDouble)
		register(String::class.java, TagCompound::set, TagCompound::getString)
		register(Pos::class.java, { tag, name, it -> tag[name] = it.toLong() }, { tag, name -> Pos.fromLong(tag.getLong(name)) })
	}

	fun <T> register(clazz: Class<T>, serializer: (TagCompound, String, T) -> Unit, deserializer: (TagCompound, String) -> T) {
		@Suppress("UNCHECKED_CAST")
		serializers.put(clazz, Pair(serializer as (TagCompound, String, Any) -> Unit, deserializer as (TagCompound, String) -> Any))
	}

	fun serialize(tag: TagCompound, obj: Any) {
		obj.javaClass.declaredFields.filter {
			obj.javaClass.isAnnotationPresent(Serialize::class.java) || it.isAnnotationPresent(Serialize::class.java)
		}.filter {
			!Modifier.isStatic(it.modifiers)
		}.forEach {
			var found = false
			var clazz = it.type
			do {
				if (serializers.containsKey(clazz)) {
					val serializer = serializers[clazz]!!.first
					it.isAccessible = true
					serializer(tag, it.name, it.get(obj))
					found = true
					break
				}
				clazz = clazz.superclass
			} while (clazz != Any::class.java)
			if (!found) {
				throw RuntimeException("Couldn't find auto serializer for ${it.name}")
			}
		}
	}

	fun deserialize(tag: TagCompound, obj: Any) {
		obj.javaClass.declaredFields.filter {
			obj.javaClass.isAnnotationPresent(Serialize::class.java) || it.isAnnotationPresent(Serialize::class.java)
		}.filter {
			!Modifier.isStatic(it.modifiers)
		}.forEach {
			var found = false
			var clazz = it.type
			do {
				if (serializers.containsKey(clazz)) {
					val deserializer = serializers[clazz]!!.second
					val modifiersField = Field::class.java.getDeclaredField("modifiers")
					modifiersField.isAccessible = true
					modifiersField.setInt(it, it.modifiers and Modifier.FINAL.inv())
					it.isAccessible = true
					it.set(obj, deserializer(tag, it.name))
					found = true
					break
				}
				clazz = clazz.superclass
			} while (clazz != Any::class.java)
			if (!found) {
				throw RuntimeException("Couldn't find auto deserializer for ${it.name}")
			}
		}
	}

}