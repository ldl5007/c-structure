package org.ldl5007.cstructure.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.ldl5007.cstructure.annotation.CStruct
import org.ldl5007.cstructure.annotation.CStructField
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class CStructProcessor : AbstractProcessor() {
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(CStruct::class.java.name)
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        roundEnv?.getElementsAnnotatedWith(CStruct::class.java)
            ?.forEach {
                processAnnotation(it)
            }
        return false
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    private fun processAnnotation(element: Element) {
        val className = element.simpleName.toString()
        val pack = processingEnv.elementUtils.getPackageOf(element).toString()

        val fileName = "${className}MetaData"
        val fileBuilder= FileSpec.builder(pack, fileName)
        val classBuilder = TypeSpec.objectBuilder(fileName)

        for (enclosed in element.enclosedElements) {

            if (enclosed.kind == ElementKind.FIELD) {
                if (enclosed.getAnnotation(CStructField::class.java) != null) {
                    classBuilder.addProperty(
                        PropertySpec.builder(enclosed.simpleName.toString(), Int::class)
                            .initializer("${element.enclosedElements.indexOf(enclosed)}")
                            .build()
                    )
                }
            }
        }

        val file = fileBuilder.addType(classBuilder.build()).build()
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGeneratedDir))
    }



}