db = db.getSiblingDB('imagesearch');

db.createUser({
  'user': 'local-user',
  'pwd': 'local-user-password',
  'roles': [
    {
      'role': 'readWrite',
      'db': 'imagesearch'
    }
  ]
});
