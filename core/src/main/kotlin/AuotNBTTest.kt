import net.shadowfacts.idlefactory.nbt.auto.AutoSerializer
import net.shadowfacts.idlefactory.nbt.auto.Serialize
import net.shadowfacts.idlefactory.nbt.impl.TagCompound

/**
 * @author shadowfacts
 */
@Serialize
data class Test(var i: Int = 0, var b: Boolean = false, var s: String = "test")

fun main(args: Array<String>) {

	val original = Test(1, true, "test2")

	println("original == $original")

	val tag = TagCompound("test")
	AutoSerializer.serialize(tag, original)

	val new = Test()
	AutoSerializer.deserialize(tag, new)

	println("new == $new")

	println(original == new)

}