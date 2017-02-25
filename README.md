#Tugas IF3111 Pengembangan Aplikasi pada Platform Khusus
#Goblin Attack

##Tujuan Pembuatan Aplikasi
Tujuan dari sistem ini adalah membantu mewujudkan salah satu tujuan Bandung Smart City dengan memberi informasi yang tepat kepada masyarakat. Dalam hal ini adalah informasi mengenai kota Bandung kepada masyarakat, khususnya mahasiswa Institut Teknologi Bandung, sehingga masyarakat dapat mengenal kota Bandung dengan cara yang menarik dan menyenangkan. Manfaat dari sistem ini adalah membuat mahasiswa Institut Teknologi Bandung mendapatkan hiburan yang bermanfaat dengan menjelajah kota Bandung serta mengenal nilai-nilai historis, tempat wisata, dan sebagainya. 

##Tim Developer
Catherine Pricilla/13514004
Alif Bhaskoro/13514016
Candra Ramsi/13514090

##Deskripsi Sistem
Sistem yang kami rancang merupakan gabungan 3 aplikasi yang dibangun di 3 platform berbeda yaitu Android, Unity dan Arduino. Sistem ini merupakan sebuah game yang menggabungkan dunia nyata dengan dunia virtual dimana user diharuskan mencari Arduino di dunia nyata yang tersambung dengan sistem game untuk mendapatkan resource yang sesuai dengan kondisi lingkungan tempat Arduino itu berada. Resource tersebut nantinya digunakan dalam game yang dibuat dengan Unity. Game yang dimainkan berlatar pada sebuah benteng dengan 4 tangga menuju puncak benteng. Goblin Akan bermunculan di depan benteng dan memanjat tangga menuju puncak benteng. Pemain harus menjatuhkan resource yang telah dikumpulkan untuk menjatuhkan goblin dengan harapan memusnahkannya. Aplikasi mobile digunakan untuk berkomunikasi antar player dan juga digunakan untuk komunikasi antar player dengan admin. Admin akan mengirimkan lokasi Arduino berada (bisa dalam instruksi arah maupun exact location) kepada player. Kemudian player harus mencari Arduino tersebut dan mengaktifkannya.Setelah aktif, player harus mengocok (shake) smartphone Android miliknya untuk mengumpulkan resource. Smartphone Android tersebut tidak boleh berjarak lebih dari 1 m dari Arduino. 

##Fungsionalitas Android
1. Aplikasi Android dapat men-autentikasi User menggunakan servis firebase<br>
2. Aplikasi Android dapat mendeteksi gesture shake dengan memanfaatkan Sensor Accelerometer<br>
3. Aplikasi Android dapat berkomunikasi data dari Arduino menggunakan jaringan nirkabel misal Bluetooth atau WiFi<br>
4. Aplikasi Android dapat mengumpulkan lokasi User via GPS<br>
5. Fitur Chat dengan User teman lain dengan FCM 6. User dapat membagikan hasil penemuannya ke Facebook dengan Intent <br>
7. Aplikasi dapat memperbaharui resource hasil penemuan via Web API<br>
8. Pemain lain akan dinotifikasi jika pemain mendapatkan resource via Push Notification<br>
9. Pemain dapat berteman jika bertemu pemain lain via bluetooth<br>
10. Pemain dapat mencari lokasi arduino menggunakan petunjuk kompas (sensor magnetik)<br>
11. Aplikasi Android dapat menyimpan pengaturan brightness layar untuk tiap user 

##Petunjuk Instalasi
Buka program dengan menggunakan aplikasi Android Studio dan lakukan run 

##Petunjuk Penggunaan
1. Lakukan login atau register
2. Pilih menu pilihan
3. Pilih chat untuk berkomunikasi dan klik histori untuk melihat
4. Pilih share untuk membagikan aplikasi ke Facebook
5. Pilih add friend, dekatkan HP ke teman, dan shake HP
6. Pilih menu lokasi untuk melihat lokasi anda
7. Pilih friend list untuk melihat teman anda