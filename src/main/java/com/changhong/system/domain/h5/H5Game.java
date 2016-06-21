package com.changhong.system.domain.h5;

import com.changhong.common.domain.EntityBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

/**
 * User: Jack Wang
 * Date: 16-6-21
 * Time: 下午6:00
 */
public class H5Game extends EntityBase {

    private static final Log logger = LogFactory.getLog(H5Game.class);

    private String gameKey;

    private String gameName;

    private String gameDescription;

    private String gameNote;

    private String gameVersion;

    private int gameScores;

    private int playTimes;

    private DateTime createTime;

    private GameStatus gameStatus;

    private GameFile appFile;

    private String gameAddress;

    private GamePosterHeng gamePosterHeng;

    private GamePosterShu gamePosterShu;

    private GameCategory gameCategory;
}
