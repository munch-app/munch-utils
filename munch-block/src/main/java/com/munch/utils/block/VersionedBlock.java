package com.munch.utils.block;

/**
 * Created by: Fuxing
 * Date: 15/9/2016
 * Time: 2:20 AM
 * Project: struct
 */
public abstract class VersionedBlock implements KeyBlock {

    public static final int VERSION_FIRST = 1;

    private int version;

    /**
     * version set to VERSION_FIRST
     */
    public VersionedBlock() {
        this(VERSION_FIRST);
    }

    /**
     * All data should be named with the version that is introduced
     * For data not named, it is there since VERSION_FIRST
     *
     * @param version number of the block
     */
    public VersionedBlock(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
