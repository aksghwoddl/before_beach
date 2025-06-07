package convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

fun Project.buildConfigConfiguration(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) = with(commonExtension) {
    buildTypes {
        getByName("release") {
            buildConfigField("boolean", "APP_DEBUG", false.toString())
        }
        getByName("debug") {
            buildConfigField("boolean", "APP_DEBUG", true.toString())
        }
    }
}