package it.gmariotti.cardslib.library;

/**
 * This is a workaround for resource resolution errors such as follows:
 *
 *    VFY: unable to resolve static field 2311 (card_options) in Lit/gmariotti/cardslib/library/R$styleable;
 *
 * To prevent the error above, this file is taken from
 *   target/.../src_managed/main/java/com/github/ikuo/garaponoid/R.java
 */
public final class R {
    public static final class attr {
        public static final int card_header_layout_resourceID=0x7f010002;
        public static final int card_layout_resourceID=0x7f010000;
        public static final int card_shadow_layout_resourceID=0x7f010001;
        public static final int card_thumbnail_layout_resourceID=0x7f010003;
        public static final int list_card_layout_resourceID=0x7f010004;
    }
    public static final class color {
        public static final int card_activated=0x7f050000;
        public static final int card_activated_kitkat=0x7f050001;
        public static final int card_background=0x7f050002;
        public static final int card_backgroundExpand=0x7f050003;
        public static final int card_background_header=0x7f050004;
        public static final int card_expand_title_color=0x7f050005;
        public static final int card_pressed=0x7f050006;
        public static final int card_pressed_kitkat=0x7f050007;
        public static final int card_text_color_header=0x7f050008;
    }
    public static final class dimen {
        public static final int card_background_default_radius=0x7f060000;
        public static final int card_base_empty_height=0x7f060001;
        public static final int card_content_outer_view_margin_bottom=0x7f060002;
        public static final int card_content_outer_view_margin_left=0x7f060003;
        public static final int card_content_outer_view_margin_right=0x7f060004;
        public static final int card_content_outer_view_margin_top=0x7f060005;
        public static final int card_expand_layout_padding=0x7f060006;
        public static final int card_expand_simple_title_paddingLeft=0x7f060007;
        public static final int card_expand_simple_title_paddingRight=0x7f060008;
        public static final int card_expand_simple_title_text_size=0x7f060009;
        public static final int card_header_button_margin_right=0x7f06000a;
        public static final int card_header_button_overflow_margin_right=0x7f06000b;
        public static final int card_header_button_padding_bottom=0x7f06000c;
        public static final int card_header_button_padding_left=0x7f06000d;
        public static final int card_header_button_padding_right=0x7f06000e;
        public static final int card_header_button_padding_top=0x7f06000f;
        public static final int card_header_outer_view_margin_bottom=0x7f060010;
        public static final int card_header_outer_view_margin_left=0x7f060011;
        public static final int card_header_outer_view_margin_right=0x7f060012;
        public static final int card_header_outer_view_margin_top=0x7f060013;
        public static final int card_header_simple_title_margin_bottom=0x7f060014;
        public static final int card_header_simple_title_margin_left=0x7f060015;
        public static final int card_header_simple_title_margin_right=0x7f060016;
        public static final int card_header_simple_title_margin_top=0x7f060017;
        public static final int card_header_simple_title_text_size=0x7f060018;
        public static final int card_main_layout_view_margin_bottom=0x7f060019;
        public static final int card_main_layout_view_margin_left=0x7f06001a;
        public static final int card_main_layout_view_margin_right=0x7f06001b;
        public static final int card_main_layout_view_margin_top=0x7f06001c;
        public static final int card_main_simple_title_margin_left=0x7f06001d;
        public static final int card_main_simple_title_margin_top=0x7f06001e;
        public static final int card_shadow_height=0x7f06001f;
        public static final int card_shadow_view_margin_bottom=0x7f060020;
        public static final int card_shadow_view_margin_left=0x7f060021;
        public static final int card_shadow_view_margin_right=0x7f060022;
        public static final int card_shadow_view_margin_top=0x7f060023;
        public static final int card_thumbnail_height=0x7f060024;
        public static final int card_thumbnail_width=0x7f060025;
        public static final int grid_card_padding_bottom=0x7f060026;
        public static final int grid_card_padding_left=0x7f060027;
        public static final int grid_card_padding_right=0x7f060028;
        public static final int grid_card_padding_top=0x7f060029;
        public static final int list_card_padding_bottom=0x7f06002a;
        public static final int list_card_padding_left=0x7f06002b;
        public static final int list_card_padding_right=0x7f06002c;
        public static final int list_card_padding_top=0x7f06002d;
    }
    public static final class drawable {
        public static final int activated_background_card=0x7f020000;
        public static final int activated_background_kitkat_card=0x7f020001;
        public static final int card_background=0x7f020002;
        public static final int card_foreground_kitkat_selector=0x7f020003;
        public static final int card_foreground_selector=0x7f020004;
        public static final int card_kitkat_selector=0x7f020005;
        public static final int card_menu_button_expand=0x7f020006;
        public static final int card_menu_button_overflow=0x7f020007;
        public static final int card_menu_button_rounded_overflow=0x7f020008;
        public static final int card_multichoice_selector=0x7f020009;
        public static final int card_selector=0x7f02000a;
        public static final int card_shadow=0x7f02000b;
        public static final int card_undo=0x7f02000c;
        public static final int ic_menu_expand_card_dark_normal=0x7f02000d;
        public static final int ic_menu_expand_card_dark_pressed=0x7f02000e;
        public static final int ic_menu_overflow_card_dark_normal=0x7f02000f;
        public static final int ic_menu_overflow_card_dark_pressed=0x7f020010;
        public static final int ic_menu_overflow_card_rounded_dark_normal=0x7f020011;
        public static final int ic_menu_overflow_card_rounded_dark_pressed=0x7f020012;
        public static final int ic_undobar_undo=0x7f020013;
        public static final int pressed_background_card=0x7f020014;
        public static final int pressed_background_kitkat_card=0x7f020015;
        public static final int undobar=0x7f020017;
        public static final int undobar_button_focused=0x7f020018;
        public static final int undobar_button_pressed=0x7f020019;
        public static final int undobar_divider=0x7f02001a;
    }
    public static final class id {
        public static final int action_preferences=0x7f0c001e;
        public static final int action_search=0x7f0c001c;
        public static final int action_sign_out=0x7f0c001d;
        public static final int card_content_expand_layout=0x7f0c000b;
        public static final int card_expand_inner_simple_title=0x7f0c0012;
        public static final int card_header_button_expand=0x7f0c0003;
        public static final int card_header_button_frame=0x7f0c0001;
        public static final int card_header_button_other=0x7f0c0004;
        public static final int card_header_button_overflow=0x7f0c0002;
        public static final int card_header_inner_frame=0x7f0c0000;
        public static final int card_header_inner_simple_title=0x7f0c0013;
        public static final int card_header_layout=0x7f0c000a;
        public static final int card_main_content_layout=0x7f0c0007;
        public static final int card_main_inner_simple_title=0x7f0c0014;
        public static final int card_main_layout=0x7f0c0009;
        public static final int card_overlap=0x7f0c000c;
        public static final int card_shadow_layout=0x7f0c0008;
        public static final int card_shadow_view=0x7f0c0005;
        public static final int card_thumb_and_content_layout=0x7f0c000d;
        public static final int card_thumbnail_image=0x7f0c0006;
        public static final int card_thumbnail_layout=0x7f0c000e;
        public static final int list_cardId=0x7f0c0015;
        public static final int list_card_undobar=0x7f0c0016;
        public static final int list_card_undobar_button=0x7f0c0018;
        public static final int list_card_undobar_message=0x7f0c0017;
        public static final int undobar=0x7f0c000f;
        public static final int undobar_button=0x7f0c0011;
        public static final int undobar_message=0x7f0c0010;
    }
    public static final class integer {
        public static final int list_card_undobar_hide_delay=0x7f070000;
    }
    public static final class layout {
        public static final int base_header_layout=0x7f030000;
        public static final int base_shadow_layout=0x7f030001;
        public static final int base_thumbnail_layout=0x7f030002;
        public static final int card_base_layout=0x7f030003;
        public static final int card_layout=0x7f030004;
        public static final int card_overlay_layout=0x7f030005;
        public static final int card_thumbnail_layout=0x7f030006;
        public static final int card_thumbnail_overlay_layout=0x7f030007;
        public static final int card_undo_layout=0x7f030008;
        public static final int inner_base_expand=0x7f030009;
        public static final int inner_base_header=0x7f03000a;
        public static final int inner_base_main=0x7f03000b;
        public static final int list_card_layout=0x7f03000c;
        public static final int list_card_thumbnail_layout=0x7f03000d;
        public static final int list_card_undo_message=0x7f03000e;
    }
    public static final class plurals {
        public static final int card_selected_items=0x7f080000;
        public static final int list_card_undo_items=0x7f080001;
    }
    public static final class string {
        public static final int list_card_undo_title=0x7f090001;
    }
    public static final class style {
        public static final int card=0x7f0a0000;
        public static final int card_base_simple_title=0x7f0a0001;
        public static final int card_content_outer_layout=0x7f0a0002;
        public static final int card_expand_simple_title=0x7f0a0003;
        public static final int card_header_button_base=0x7f0a0004;
        public static final int card_header_button_base_expand=0x7f0a0005;
        public static final int card_header_button_base_other=0x7f0a0006;
        public static final int card_header_button_base_overflow=0x7f0a0007;
        public static final int card_header_button_frame=0x7f0a0008;
        public static final int card_header_compound_view=0x7f0a0009;
        public static final int card_header_outer_layout=0x7f0a000a;
        public static final int card_header_simple_title=0x7f0a000b;
        public static final int card_main_contentExpand=0x7f0a000c;
        public static final int card_main_layout=0x7f0a000d;
        public static final int card_main_layout_kitkat=0x7f0a000e;
        public static final int card_main_layout_foreground=0x7f0a000f;
        public static final int card_main_layout_foreground_kitkat=0x7f0a0010;
        public static final int card_shadow_image=0x7f0a0011;
        public static final int card_shadow_outer_layout=0x7f0a0012;
        public static final int card_thumbnail_compound_view=0x7f0a0013;
        public static final int card_thumbnail_image=0x7f0a0014;
        public static final int card_thumbnail_outer_layout=0x7f0a0015;
        public static final int grid_card=0x7f0a0016;
        public static final int list_card=0x7f0a0017;
        public static final int list_card_base=0x7f0a0018;
        public static final int list_card_thumbnail=0x7f0a0019;
        public static final int list_card_UndoBar=0x7f0a001a;
        public static final int list_card_UndoBarButton=0x7f0a001b;
        public static final int list_card_UndoBarMessage=0x7f0a001c;
    }
    public static final class styleable {
        public static final int[] ForegroundLinearLayout = {
            0x01010109, 0x01010200, 0x010103f1
        };
        public static final int ForegroundLinearLayout_android_foreground = 0;
        public static final int ForegroundLinearLayout_android_foregroundGravity = 1;
        public static final int ForegroundLinearLayout_android_foregroundInsidePadding = 2;
        public static final int[] card_options = {
            0x7f010000, 0x7f010001, 0x7f010002, 0x7f010003,
            0x7f010004
        };
        public static final int card_options_card_header_layout_resourceID = 2;
        public static final int card_options_card_layout_resourceID = 0;
        public static final int card_options_card_shadow_layout_resourceID = 1;
        public static final int card_options_card_thumbnail_layout_resourceID = 3;
        public static final int card_options_list_card_layout_resourceID = 4;
    };
}
