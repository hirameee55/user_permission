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
  Future<int?> checkOp(UserPermissionType type) async => 1;

  @override
  Future<int?> startWatching(UserPermissionType type, String? myClass) async =>
      0;
}

void main() {
  final UserPermissionPlatform initialPlatform =
      UserPermissionPlatform.instance;

  test('$MethodChannelUserPermission is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelUserPermission>());
  });

  test('checkOp', () async {
    UserPermission userPermission = UserPermission();
    MockUserPermissionPlatform fakePlatform = MockUserPermissionPlatform();
    UserPermissionPlatform.instance = fakePlatform;

    expect(await userPermission.checkOp(UserPermissionType.usageStats), 1);
  });

  test('startWatching', () async {
    UserPermission userPermission = UserPermission();
    MockUserPermissionPlatform fakePlatform = MockUserPermissionPlatform();
    UserPermissionPlatform.instance = fakePlatform;

    expect(
        await userPermission.startWatching(UserPermissionType.usageStats), 0);
  });
}
