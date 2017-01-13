package org.guppy4j;

import org.guppy4j.log.LogProvider;
import org.guppy4j.text.NamedString;
import org.guppy4j.text.NamedStrings;
import org.guppy4j.text.NamedStringsImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Type-safe access to standard system properties
 */
public class SystemInfoImpl implements SystemInfo {

    private final NamedStrings systemValues;

    public SystemInfoImpl(LogProvider logProvider) {
        this.systemValues = new NamedStringsImpl(logProvider,
                System.getProperties(), "System properties");
    }

    @Override
    public char fileSeparator() {
        return character("file.separator");
    }

    @Override
    public char pathSeparator() {
        return character("path.separator");
    }

    @Override
    public String lineSeparator() {
        return notNullValue("line.separator");
    }

    @Override
    public Iterable<Path> javaClassPath() {
        return splitPaths(get("java.class.path"), pathSeparator());
    }

    @Override
    public Iterable<Path> javaExtDirs() {
        return splitPaths(get("java.ext.dirs"), pathSeparator());
    }

    @Override
    public Path javaHome() {
        return getPath("java.home");
    }

    @Override
    public Path javaIoTmpDir() {
        return getPath("java.io.tmpdir");
    }

    @Override
    public Path userDir() {
        return getPath("user.dir");
    }

    @Override
    public Path userHome() {
        return getPath("user.home");
    }

    @Override
    public String javaCompiler() {
        return notNullValue("java.compiler");
    }

    @Override
    public String javaVendor() {
        return notNullValue("java.vendor");
    }

    @Override
    public URL javaVendorUrl() {
        try {
            return new URL(notNullValue("java.vendor.url"));
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String javaVersion() {
        return notNullValue("java.version");
    }

    @Override
    public String osArch() {
        return notNullValue("os.arch");
    }

    @Override
    public String osName() {
        return notNullValue("os.name");
    }

    @Override
    public String osVersion() {
        return notNullValue("os.version");
    }

    @Override
    public String userName() {
        return notNullValue("user.name");
    }

    private static Iterable<Path> splitPaths(NamedString namedString, char separator) {
        final String[] pathItems = namedString.splitBy(separator);
        final Collection<Path> paths = new ArrayList<>(pathItems.length);
        for (String pathItem : pathItems) {
            paths.add(Paths.get(pathItem));
        }
        return paths;
    }

    private Path getPath(String name) {
        return Paths.get(notNullValue(name));
    }

    private char character(String name) {
        return get(name).asChar();
    }

    private String notNullValue(String name) {
        return get(name).getNotNull();
    }

    private NamedString get(String name) {
        return systemValues.get(name);
    }
}
