import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:user_permission/user_permission_method_channel.dart';
import 'package:user_permission/user_permission_type.dart';

abstract class UserPermissionPlatform extends PlatformInterface {
  UserPermissionPlatform() : super(token: _token);
  static final Object _token = Object();
  static UserPermissionPlatform _instance = MethodChannelUserPermission();
  static UserPermissionPlatform get instance => _instance;

  static set instance(UserPermissionPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<int?> state(UserPermissionType type) {
    throw UnimplementedError('state() has not been implemented.');
  }

  Future<int?> startWatching(UserPermissionType type, String? myClass) {
    throw UnimplementedError('startWatching() has not been implemented.');
  }
}
