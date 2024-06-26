import 'package:flutter_test/flutter_test.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:user_permission/user_permission.dart';
import 'package:user_permission/user_permission_method_channel.dart';
import 'package:user_permission/user_permission_platform_interface.dart';
import 'package:user_permission/user_permission_type.dart';

class MockUserPermissionPlatform
    with MockPlatformInterfaceMixin
    implements UserPermissionPlatform {
  @override
  Future<UserPermissionState> state(UserPermissionType type) async =>
      UserPermissionState.granted;

  @override
  Future<UserPermissionState> startWatching(
          UserPermissionType type, String? myClass) async =>
      UserPermissionState.denied;
}

void main() {
  final UserPermissionPlatform initialPlatform =
      UserPermissionPlatform.instance;

  test('$MethodChannelUserPermission is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelUserPermission>());
  });

  test('state', () async {
    UserPermission userPermission = UserPermission();
    MockUserPermissionPlatform fakePlatform = MockUserPermissionPlatform();
    UserPermissionPlatform.instance = fakePlatform;

    expect(await userPermission.state(UserPermissionType.usageStats),
        UserPermissionState.granted);
  });

  test('startWatching', () async {
    UserPermission userPermission = UserPermission();
    MockUserPermissionPlatform fakePlatform = MockUserPermissionPlatform();
    UserPermissionPlatform.instance = fakePlatform;

    expect(await userPermission.startWatching(UserPermissionType.usageStats),
        UserPermissionState.denied);
  });
}
