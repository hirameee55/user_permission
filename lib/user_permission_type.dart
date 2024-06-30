/// Define user permissions for operations.
enum UserPermissionType {
  /// Usage access
  usageStats,

  /// Display over other apps
  systemAlertWindow,

  /// Picture-in-picture
  pictureInPicture,

  /// Modify system settings
  writeSettings,

  /// Alarm & reminders
  ///
  /// Only supported on Android API 31+.
  scheduleExactAlarm,

  /// Accessibility
  accessibilitySettings,
}

enum UserPermissionState {
  denied,
  granted,
}

extension UserPermissionStateValue on UserPermissionState {
  int get value {
    switch (this) {
      case UserPermissionState.denied:
        return 0;
      case UserPermissionState.granted:
        return 1;
    }
  }

  bool get isDenied {
    switch (this) {
      case UserPermissionState.denied:
        return true;
      case UserPermissionState.granted:
        return false;
    }
  }

  bool get isGranted {
    switch (this) {
      case UserPermissionState.denied:
        return false;
      case UserPermissionState.granted:
        return true;
    }
  }

  static UserPermissionState statusByValue(int? value) {
    if (value == null) return UserPermissionState.denied;

    return UserPermissionState.values
        .firstWhere((element) => element.value == value);
  }
}
