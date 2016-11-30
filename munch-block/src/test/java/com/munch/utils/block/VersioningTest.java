package com.munch.utils.block;

import org.junit.jupiter.api.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by: Fuxing
 * Date: 30/11/2016
 * Time: 7:26 PM
 * Project: munch-utils
 */
class VersioningTest {

    @Test
    void versionChange() throws Exception {
        FirstBlock first = new FirstBlock();
        UpdatedBlock update = new UpdatedBlock();

        assertThat(first.getVersion()).isEqualTo(VersionedBlock.VERSION_FIRST);
        assertThat(update.getVersion()).isEqualTo(5);
        assertThat(first.getVersion()).isNotEqualTo(update.getVersion());
    }

    class FirstBlock extends VersionedBlock {
    }

    class UpdatedBlock extends VersionedBlock {
        public UpdatedBlock() {
            super(5);
        }
    }
}
