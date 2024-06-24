import 'package:user_permission/user_permission_method_channel.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:user_permission/user_permission_type.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();
  MethodChannelUserPermission platform = MethodChannelUserPermission();

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(
      platform.methodChannel,
      (methodCall) async {
        switch (methodCall.method) {
          case 'state':
            return 0;

          case 'startWatching':
            return 1;
        }

        return null;
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(platform.methodChannel, null);
  });

  test('state', () async {
    expect(await platform.state(UserPermissionType.systemAlertWindow), 0);
  });

  test('startWatching', () async {
    expect(
        await platform.startWatching(
            UserPermissionType.systemAlertWindow, null),
        1);
  });
}
