#import "EmhScanPlugin.h"
#if __has_include(<emh_scan/emh_scan-Swift.h>)
#import <emh_scan/emh_scan-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "emh_scan-Swift.h"
#endif

@implementation EmhScanPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftEmhScanPlugin registerWithRegistrar:registrar];
}
@end
