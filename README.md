# VersionComparator

Comparator for application versions.

Default comparator works with almost any versioning scheme out-of-box.

For example, it can sort this:

    5.0.43.21
    5.0.32.34
    5.0.32-alpha.34
    6.42b7-rc
    5.0.32-beta.34
    5.0.32-rc.34
    5.0.32-1.34
    6.42
    4.1
    6.42b10
    5.0.43.21-SNAPSHOT
    6.42b7
    6.42b
    1.00-rc2
    1.00
    1.00-rc1
    6.42b7-rc2
    4.1.1
    1.00-rc
    
to

    1.00-rc
    1.00-rc1
    1.00-rc2
    1.00
    4.1
    4.1.1
    5.0.32-alpha.34
    5.0.32-beta.34
    5.0.32-rc.34
    5.0.32.34
    5.0.32-1.34
    5.0.43.21-SNAPSHOT
    5.0.43.21
    6.42
    6.42b
    6.42b7-rc
    6.42b7-rc2
    6.42b7
    6.42b19

## Usage

Example:
    
    VersionComparator.createDefault().compare(v1, v2)
    
#### Maven

    <dependency>
        <groupId>com.github.rshift</groupId>
        <artifactId>vercmp</artifactId>
        <version>1.0.0</version>
    </dependency>

#### Gradle
    
    compile("com.github.rshift:vercmp:1.0.0")
