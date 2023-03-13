package com.zyk.music.entity.vo;

import com.zyk.music.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class MusicBo extends Music {
        private int index;
        private boolean ifLove;
}
