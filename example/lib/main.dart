import 'package:flutter/material.dart';
import 'package:user_permission/user_permission.dart';
import 'package:user_permission/user_permission_type.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _permission = UserPermission();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('UserPermission'),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: [
              OutlinedButton(
                child: const Text('usageStats'),
                onPressed: () async {
                  await _userPermission(UserPermissionType.usageStats);
                },
              ),
              const SizedBox(height: 10),
              OutlinedButton(
                child: const Text('systemAlertWindow'),
                onPressed: () async {
                  await _userPermission(UserPermissionType.systemAlertWindow);
                },
              ),
              const SizedBox(height: 10),
              OutlinedButton(
                child: const Text('pictureInPicture'),
                onPressed: () async {
                  await _userPermission(UserPermissionType.pictureInPicture);
                },
              ),
              const SizedBox(height: 10),
              OutlinedButton(
                child: const Text('writeSettings'),
                onPressed: () async {
                  await _userPermission(UserPermissionType.writeSettings);
                },
              ),
              const SizedBox(height: 10),
              OutlinedButton(
                child: const Text('scheduleExactAlarm'),
                onPressed: () async {
                  await _userPermission(UserPermissionType.scheduleExactAlarm);
                },
              ),
              const SizedBox(height: 10),
              OutlinedButton(
                child: const Text('accessibilitySettings'),
                onPressed: () async {
                  await _userPermission(
                      UserPermissionType.accessibilitySettings);
                },
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<void> _userPermission(UserPermissionType type) async {
    await _permission.state(type);
    await _permission.startWatching(type);
  }
}
