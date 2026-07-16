# This project study Android Native
Lộ trình học Interface and Navigation
1. Interface (UI) convert Flutter
   LinearLayout -> Column/Row(android:orientation)
   ConstraintLayout -> Scaffold + bố cục tự do
   FrameLayout -> Stack
   ScrollView -> SingleChildScrollView
   RecyclerView -> ListView.builder
   Button -> Button
   TextView -> Text
   ImageView -> Image
   EditText -> TextField
-> ViewBinding (binding.btnLogin.setOnClickListener {})
-> RecyclerView (Adapter, ViewHolder, notifyItemChanged, notifyDataSetChanged)
2. Navigation
    Android cũ (Activity -> Intent -> Activity)
    AndRoid google (Main Activity -> Fragment -> Fragment -> Fragment)
    dùng findNavController().navigate(...)
3. Fragment
4. Material Design
   Toolbar
   BottomNavigation
    NavigationDrawer
    Dialog
    Snackbar
    BottomSheet
5. Jetpack Compose
    
