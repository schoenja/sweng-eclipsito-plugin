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
Copy the plugin jar file into your project. Then include it as shown in the example below.

Example:
```groovy
buildscript {
    dependencies {
        classpath files('eclipsito-plugin/eclipsito-0.1.1-RC1.jar')
    }
}

apply plugin: 'unibas.sweng.eclipsito.EclipsitoPlugin'
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
| `resourcesDirectory` | takes a `file('..')` object | foo |
| `eclipsitoJar` | takes a `file('..')` object | foo |

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
