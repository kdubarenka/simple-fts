package net.duborenko.fts

import kotlin.reflect.KProperty

/**
 * @author Kiryl Dubarenka
 */
object ReflectionUtils {

    private fun <Doc: Any> listProperties(doc: Doc) =
        doc::class.members.asSequence()
                .filter { it is KProperty }

    private fun <Doc : Any> annotatedValuesSequence(doc: Doc, annotationType: Class<*>): Sequence<Any?> {
        return listProperties(doc)
                .filter { it.annotations.find { a -> a.annotationClass.java == annotationType } != null }
                .map { it.call(doc) }
    }

    fun <Doc: Any> listAnnotatedValues(doc: Doc, annotationType: Class<*>) =
            annotatedValuesSequence(doc, annotationType).toList()

    fun <Doc: Any> getAnnotatedValue(doc: Doc, annotationType: Class<*>) =
            annotatedValuesSequence(doc, annotationType).first()

}