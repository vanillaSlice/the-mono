db = db.getSiblingDB('admin');

db.createUser({
  'user': 'local-user',
  'pwd': 'local-user-password',
  'roles': [
    {
      'role': 'readWrite',
      'db': 'admin'
    }
  ]
});
