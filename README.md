# user_permission

This plugin provides Android applications with the ability to obtain the status of special app access and detect changes. Specifically, it has the following functions:

1. Get status of special app access:

    This plugin checks whether the app has been granted special app access permission.

2. Launch screen for status change:

    It has the ability to launch the settings screen required for granting special app access. Through this plugin, users can access the settings screen to easily grant the necessary permissions to the app.

3. Detect status change:

    It can detect changes in the status of special app access in real time. This allows you to automatically return to your app from the settings screen.

## Platform Support

| Android |
| :-----: |
|   ✅    |

## Supported User Permissions

| User Permission | Supported API Level | Note |
| :----- | :----- | :----- |
| Usage access                       | 23+ | |
| Display over other apps            | 23+ | |
| Picture-in-picture                 | 23+ | |
| Modify system settings             | 23+ | |
| Alarm & reminders                  | 31+ | When this user permission is turned off, your app process will be terminated. |
| Accessibility                      | 23+ | When this user permission is turned off, your app process will be terminated. |
| Notification read, reply & control | 23+ | |

## Usage

To use this plugin, add `user_permission` as a dependency in your pubspec.yaml file.

### Get status

```dart
import 'package:user_permission/user_permission.dart';
import 'package:user_permission/user_permission_type.dart';

final state = await UserPermission().state(UserPermissionType.usageStats);
```

### Launch screen & Detect status change

```dart
import 'package:user_permission/user_permission.dart';
import 'package:user_permission/user_permission_type.dart';

final state = await UserPermission().startWatching(UserPermissionType.systemAlertWindow);
```

### Status value

The status can be either `denied` or `granted`.  
If denied, it's a good idea to ask the user for permission with `startWatching`.

```dart
import 'package:user_permission/user_permission.dart';
import 'package:user_permission/user_permission_type.dart';

final userPermission = UserPermission();
final state = await userPermission.state(UserPermissionType.usageStats);
if (state.isDenied) {
    await userPermission.startWatching(UserPermissionType.usageStats);
}
```

## Learn more

- [API Documentation](https://pub.dev/documentation/user_permission/latest/)
