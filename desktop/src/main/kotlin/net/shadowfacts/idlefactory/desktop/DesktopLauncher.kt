package net.shadowfacts.idlefactory.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import net.shadowfacts.idlefactory.IdleFactory

fun main(args: Array<String>) {
	val config = LwjglApplicationConfiguration()
	LwjglApplication(IdleFactory, config)
}