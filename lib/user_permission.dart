import 'package:user_permission/user_permission_platform_interface.dart';
import 'package:user_permission/user_permission_type.dart';

class UserPermission {
  Future<UserPermissionState> state(UserPermissionType type) {
    return UserPermissionPlatform.instance.state(type);
  }

  Future<UserPermissionState> startWatching(UserPermissionType type,
      {String? myClass}) {
    return UserPermissionPlatform.instance.startWatching(type, myClass);
  }
}
