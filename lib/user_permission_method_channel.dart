import 'package:user_permission/user_permission_platform_interface.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:user_permission/user_permission_type.dart';

class MethodChannelUserPermission extends UserPermissionPlatform {
  @visibleForTesting
  final methodChannel =
      const MethodChannel('com.hirameee.plugin/user_permission');

  @override
  Future<int?> state(UserPermissionType type) async {
    final state = await methodChannel.invokeMethod<int>('state', {
      'type': type.name,
    });
    return state;
  }

  @override
  Future<int?> startWatching(UserPermissionType type, String? myClass) async {
    final state = await methodChannel.invokeMethod<int>('startWatching', {
      'type': type.name,
      'myClass': myClass,
    });
    return state;
  }
}
