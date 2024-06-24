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

The status is returned as an int.

| Android parameters | value |
| :-----: | :-----: |
| [MODE_ALLOWED](https://developer.android.com/reference/android/app/AppOpsManager#MODE_ALLOWED) | 0 |
| [MODE_IGNORED](https://developer.android.com/reference/android/app/AppOpsManager#MODE_IGNORED) | 1 |
| [MODE_ERRORED](https://developer.android.com/reference/android/app/AppOpsManager#MODE_ERRORED) | 2 |
| [MODE_DEFAULT](https://developer.android.com/reference/android/app/AppOpsManager#MODE_DEFAULT) | 3 |
| [MODE_FOREGROUND](https://developer.android.com/reference/android/app/AppOpsManager#MODE_FOREGROUND) | 4 |

## Learn more

- [API Documentation](https://pub.dev/documentation/user_permission/latest/)
