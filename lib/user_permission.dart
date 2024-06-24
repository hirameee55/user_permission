import 'package:user_permission/user_permission_platform_interface.dart';
import 'package:user_permission/user_permission_type.dart';

class UserPermission {
  Future<int?> state(UserPermissionType type) {
    return UserPermissionPlatform.instance.state(type);
  }

  Future<int?> startWatching(UserPermissionType type, {String? myClass}) {
    return UserPermissionPlatform.instance.startWatching(type, myClass);
  }
}
