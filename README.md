# eclipsito plugin for gradle

## About
This is a custom plugin developed for the software engineering lecture.
It hides the overhead needed for eclipsito in the ganttproject by providing 
a gradle like interface to its functionality.

This is not meant as a general use plugin. It is written specifically for 
the ganttproject. Use this at your own risk!

## Important
This plugin only works if used with a **single** eclipsito module. This was 
done to simplify the plugin. Multi module support might be possible, but as 
the overall goal is to remove eclipsito as much as possible, it was not implemented.

## Include in gradle build file
The plugin is available [in the official gradle repository](https://plugins.gradle.org/plugin/schoenja.sweng.eclipsito.EclipsitoPlugin).

Example:
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.schoenja.sweng.eclipsito:eclipsito:0.3.0"
  }
}

apply plugin: "schoenja.sweng.eclipsito.EclipsitoPlugin"
```

or, if you are using Gradle v2.1 or higher and are working in the main project folder:
```groovy
plugins {
  id "schoenja.sweng.eclipsito.EclipsitoPlugin" version "0.3.0"
}
```

## Usage
The plugin provides the following tasks:

- `createPluginXML` which generates a plugin.xml file for eclipsito based on the config set.
- `copyPlugin` which emulates the old task with the same name based on the config set.

### Configuration
Configuration is handled in the `eclipsito` block.

#### General configuration

| Name | Argument | Description |
| ---- | -------- | ----------- |
| `pluginXmlFile` | takes a `file('..')` object | Specifies the `plugin.xml` file. It will be written as `plugin.xml` into the finished eclipsito module folder, no matter the name. |
| `pluginsDirectory` | takes a `file('..')` object | Target directory for the eclipsito plugins. |
| `distBinDirectory` | takes a `file('..')` object | Target directory for the whole eclipsito setup. |
| `ganttprojectJarFile` | takes a `file('..')` object | The generated jar file of the eclipsito plugin. |
| `resourcesDirectory` | takes a `file('..')` object | The directory that contains the java resource directory (typically 'src/main/resources') |
| `eclipsitoJar` | takes a `file('..')` object | The 'eclipsito.jar' file to use. |
| `eclipsitoConfigFile` | takes a `file('..')` object | The config file for 'eclipsito.jar'. |
| `eclipsitoJarCollection` | takes a `fileCollection('..')` object | A file collection representing the 'eclipsito.jar' file. Always have this as `zipTree(eclipsitoJar)` unless you really know what you are doing! |

#### Extension points
Extension points are used to define possible extensions in eclipsito.

Syntax:
`extensionpoint id: '<id>', name: '<name>', xml: '<xml>', data: <data>`

| Argument | Expected value | Description |
| -------- | -------------- | ----------- |
| `<id>`   | String         | The ID of the extension point, used by the code. |
| `<name>` | String         | The name of the extension point, used by the extension statements. |
| `<xml>`  | String         | Name of the xml tag used in the plugin.xml file. |
| `<data>` | Groovy Map     | A map of arguments for the extension. The key is the name of the argument used by the extension syntax, the value is the string used in the XML tag. A key starting with a `_` marks an optional argument. |

Examples:
```groovy
    extensionpoint id: 'exporter', name: 'Exporter', xml: 'exporter', data: [name: 'class']
    extensionpoint id: 'impex.htmlpdf.FontDirectory', name: 'Font Directory', xml: 'dir', data: [name: 'name', _absolute: 'absolute']
```

#### Extensions
Extensions are added features in eclipsito.

Syntax:
`extension point: <point>, <arguments>`

`<point>` is the extension point to be used, represented by its `name`.

`<arguments>` are the arguments set by the extension point.

Example:
```groovy
    extension point: 'Exporter', name: 'biz.ganttproject.export.ExporterToImage'
    extension point: 'Exporter', name: 'biz.ganttproject.export.ExporterToCSV'
    extension point: 'Font Directory', name: '/System/Library/Fonts', absolute: 'true'
    extension point: 'Font Directory', name: 'fonts'
```

#### Example

```groovy
eclipsito {
    pluginXMLFile = file("testplugin.xml")
    pluginsDirectory = file("../ganttproject-builder/dist-bin/plugins-2.8.5")
    distBinDirectory = file("../ganttproject-builder/dist-bin/")
    ganttprojectJarFile = jar.outputs.getFiles().getFiles().flatten()[0]
    resourcesDirectory = file("src/main/resources")
    eclipsitoJar = file("lib/patched/eclipsito.jar")

    extensionpoint id: 'exporter', name: 'Exporter', xml: 'exporter', data: [name: 'class']
    
    extension point: 'Exporter', name: 'biz.ganttproject.export.ExporterToImage'
    extension point: 'Exporter', name: 'biz.ganttproject.export.ExporterToCSV'
}
```


## Choice of programming language
The plugin was implemented using [Kotlin](http://kotlinlang.org/).
This was done due to Java causing issues and my personal lack of knowledge 
of the groovy language. Kotlin is sufficiently similar to Java to allow 
Java developers to easily understand Kotlin code.
