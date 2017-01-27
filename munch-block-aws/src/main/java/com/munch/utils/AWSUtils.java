package com.munch.utils;

import com.amazonaws.auth.*;

/**
 * Created By: Fuxing Loh
 * Date: 2/1/2017
 * Time: 3:47 PM
 * Project: corpus-catalyst
 */
public final class AWSUtils {

    private AWSUtils() {/**/}

    /**
     * AwsUtils Credential provider read from .conf files with typesafe config library
     * Supported provider values are "system" & "ecs" & "ec2" & "env"
     * amazon.key.provider = {}
     * <p>
     * See http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
     *
     * @return AWSCredentialsProvider
     */
    public static AWSCredentialsProvider getCredentials(String type) {
        switch (type) {
            case "ecs":
                return new ContainerCredentialsProvider();
            case "ec2":
                return InstanceProfileCredentialsProvider.getInstance();
            case "env":
                return new EnvironmentVariableCredentialsProvider();
            case "system":
                return new SystemPropertiesCredentialsProvider();
            default:
                throw new RuntimeException("Supported amazon.key.provider are ec2, ec2, env & system.");
        }
    }
}
