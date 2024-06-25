import 'package:user_permission/user_permission_platform_interface.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:user_permission/user_permission_type.dart';

class MethodChannelUserPermission extends UserPermissionPlatform {
  @visibleForTesting
  final methodChannel =
      const MethodChannel('com.hirameee.plugin/user_permission');

  @override
  Future<UserPermissionState> state(UserPermissionType type) async {
    final value = await methodChannel.invokeMethod<int>('state', {
      'type': type.name,
    });

    return UserPermissionStateValue.statusByValue(value);
  }

  @override
  Future<UserPermissionState> startWatching(
      UserPermissionType type, String? myClass) async {
    final value = await methodChannel.invokeMethod<int>('startWatching', {
      'type': type.name,
      'myClass': myClass,
    });

    return UserPermissionStateValue.statusByValue(value);
  }
}
