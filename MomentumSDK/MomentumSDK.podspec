Pod::Spec.new do |spec|
    spec.name                     = 'MomentumSDK'
    spec.version                  = '1.0.2'
    spec.homepage                 = 'https://momentumindiana.org'
    spec.source                   = { :http=> ''}
    spec.authors                  = 'Mwai Banda'
    spec.license                  = 'MIT'
    spec.summary                  = '
        The Momentum SDK facilitates seamlessly integration with auth, networking,
        database(local & cloud) & caching functionality for Android, iOS & iPadOS. 
        Copyright © 2022 Momentum. All rights reserved.
        '
    spec.vendored_frameworks      = 'build/cocoapods/framework/MomentumSDK.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target    = '14.1'
                
                
    if !Dir.exist?('build/cocoapods/framework/MomentumSDK.framework') || Dir.empty?('build/cocoapods/framework/MomentumSDK.framework')
        raise "

        Kotlin framework 'MomentumSDK' doesn't exist yet, so a proper Xcode project can't be generated.
        'pod install' should be executed after running ':generateDummyFramework' Gradle task:

            ./gradlew :MomentumSDK:generateDummyFramework

        Alternatively, proper pod installation is performed during Gradle sync in the IDE (if Podfile location is set)"
    end
                
    spec.xcconfig = {
        'ENABLE_USER_SCRIPT_SANDBOXING' => 'NO',
    }
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':MomentumSDK',
        'PRODUCT_MODULE_NAME' => 'MomentumSDK',
    }
                
    spec.script_phases = [
        {
            :name => 'Build MomentumSDK',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
                
end