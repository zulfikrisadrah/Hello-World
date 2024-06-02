# Hello World
Hello World adalah sebuah aplikasi mobile yang menawarkan pengalaman belajar yang menyenangkan dengan sentuhan permainan. Aplikasi ini menggabungkan tema edukasi dengan game untuk membuat pembelajaran menjadi lebih interaktif dan menarik. Dengan menyelesaikan level-level dalam permainan, pengguna memiliki kesempatan untuk menjelajahi detail tentang berbagai negara di dunia.

# Fitur Utama
1.	**Permainan**: Hello World menawarkan dua jenis permainan untuk menguji pengetahuan pengguna tentang ibu kota dan mata uang negara.
2.	**Life dan Coin**: Pengguna memiliki life dan coin dalam permainan, di mana life akan berkurang jika menjawab salah dan coin akan bertambah jika menjawab benar.
3.	**Store**: Pengguna memiliki opsi untuk membeli nyawa dengan menggunakan koin dalam permainan.
4.	**Daftar Negara**: Fitur ini menampilkan daftar seluruh nama negara, dilengkapi dengan fitur pencarian dan filter.
5.	**Detail Negara**: Memberikan informasi lengkap seperti jumlah penduduk, luas negara, ibu kota, mata uang, dll.
6.	**Redeem Code**: Pengguna dapat memperoleh hadiah berupa life atau coin dengan memasukkan kode rahasia.

# Cara Penggunaan
1.	Saat pertama kali menginstall dan menjalankan aplikasi, pengguna akan memiliki 5 life dan 5 coin. Detail negara-negara juga terkunci dan level tiap permainan juga akan berada di level 1. 
2.	**Home**: Halaman pertama yang diakses setelah pengguna masuk ke aplikasi adalah Home. Di sini, pengguna dapat melihat jumlah life dan coin yang mereka miliki, serta memulai permainan dari halaman ini setelah menekan tombol "play" di sebelah jenis permainan yang ingin dimainkan.
3.	**Permainan**: Pada halaman permainan, terdapat teks yang menampilkan level pengguna, jumlah life, dan coin untuk mempermudah pengguna mengetahuinya. Jika pengguna memilih jawaban yang salah, jawaban akan berubah menjadi merah dan jumlah life pengguna akan berkurang 1. Namun, jika pengguna memilih jawaban yang benar, mereka akan mendapatkan hadiah 3 coin. Setelah menjawab dengan benar, akan muncul dialog yang menanyakan apakah pengguna ingin melanjutkan ke level selanjutnya atau kembali ke halaman Home.
4.	**Store**: Halaman ini adalah tempat pengguna dapat membeli nyawa dengan membayar coin dalam jumlah tertentu. Jumlah coin dan life juga ditampilkan di halaman ini agar pengguna dapat mengetahui jumlah life dan coin yang mereka miliki tanpa harus kembali ke halaman Home. Terdapat 4 pilihan di toko, dan akan muncul dialog konfirmasi pembelian jika salah satu pilihan dipilih.
5.	**Detail**: Halaman ini akan menampilkan daftar nama negara yang tersedia namun semuanya masih terkunci. Pengguna dapat melakukan pencarian berdasarkan nama negara, dan filter berdasarkan status negara terkunci atau tidak juga tersedia di sini. Jika pengguna ingin melihat detail suatu negara, mereka harus membayar 1 coin terlebih dahulu, dan negara tersebut tidak akan terkunci lagi setelahnya.
6.	**Redeem Code**: Halaman ini memungkinkan pengguna untuk menggunakan atau mencoba memasukkan kode rahasia yang telah disediakan. Jika kode rahasia berhasil dimasukkan, pengguna akan mendapatkan hadiah berupa life atau coin.

# Implementasi Teknis
1.	**Menginstal dan Menjalankan Aplikasi**: Ketika pengguna pertama kali masuk ke aplikasi, data eksternal yang diambil dari API akan disimpan di dalam database SQLite. Hal ini memungkinkan akses data meskipun internet tidak aktif. Selain itu, informasi seperti jumlah life, coin, dan level permainan akan disimpan di SharedPreferences.
2.	**Mendapatkan Life Gratis**: Pada MainActivity, pengguna dapat memperoleh 1 life secara gratis jika jumlah life mereka kurang dari 5. Life akan bertambah 1 setiap 5 menit hingga mencapai batas maksimum 5 life. Proses ini diatur menggunakan kode lifeHandler.postDelayed(this, 30000).
3.	**Memulai Permainan**: Permainan dapat dimulai dengan menekan tombol play pada jenis permainan yang tersedia di halaman Home. Nama negara dan opsi pilihan akan dipilih secara acak menggunakan method dbHelper.getRandomCountry(). Pertanyaan dan opsi pilihan akan disimpan di SharedPreferences, memastikan konsistensi permainan hingga level diselesaikan.
4.	**Membeli Life**: Pengguna dapat membeli life di halaman Store dengan memilih salah satu dari 4 opsi yang telah ditentukan. Jika pengguna mengonfirmasi pembelian, proses performPurchase(life, coin) akan dijalankan untuk mengurangi jumlah coin dan menambahkan jumlah life sesuai dengan pembelian.
5.	**Menampilkan Daftar Negara**: Daftar negara ditampilkan menggunakan RecyclerView setelah mengambil data dari API yang ditentukan.
6.	**Menampilkan Detail Negara**: Detail negara dapat diakses jika atribut "locked" negara telah berubah menjadi false. Perubahan atribut ini terjadi setelah pembayaran dilakukan saat pengguna pertama kali ingin melihat detail negara. Method showUnlockDialog(Country country) pada class CountryAdapter digunakan untuk proses ini.
7.	**Pencarian dan Filter pada Daftar Negara**: Hasil pencarian akan ditampilkan berdasarkan persamaan inputan dan nama negara yang tersedia melalui implementasi method performFiltering(CharSequence constraint) pada class CountryAdapter. Hasil pemilihan filter akan ditampilkan berdasarkan atribut negara yang telah ditambahkan yaitu “locked” dengan menggunakan implementasi method applyFilters() pada class DetailFragment.
8.	**Redeem Code**: Pengguna dapat memasukkan kode rahasia untuk memperoleh hadiah berupa life atau coin. Proses ini dikelola dengan method redeemCode(String inputCode) yang memeriksa validitas kode yang dimasukkan pengguna dengan kode yang telah ditentukan sebelumnya.

# Author
[Github](https://github.com/zulfikrisadrah)

[Instagram](https://www.instagram.com/mzulfikrisadrah/)

# Referensi
[Countries API](https://freetestapi.com/apis/countries

[Design 1](https://dribbble.com/shots/18544068-A-Quiz-App)

[Design 2](https://dribbble.com/shots/17444278-Quize-Game-Mobile-App)
