package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGdxGame extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    
    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("Particle Park UI.json"));
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Image image = new Image(skin, "rocket");
        root.add(image).expand();
        
        final TextButton textButton = new TextButton("Show Editor", skin);
        root.add(textButton).top();
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Vector2 vector2 = textButton.localToStageCoordinates(new Vector2(textButton.getWidth() - 10, -10));
                showWindow(vector2.x, vector2.y);
            }
        });
    }
    
    /**
     * Specify the upper right coordinate for the window's position
     * @param x
     * @param y 
     */
    private void showWindow(float x, float y) {
        final Window window = new Window("Particle Park UI", skin);
        window.getTitleTable().pad(5);
        
        Table table = new Table();
        table.pad(5);
        window.add(table).grow();
        
        table.defaults().space(10);
        Label label = new Label("Login / New User", skin, "subtitle");
        table.add(label).left();
        
        table.row();
        TextField textField = new TextField("", skin);
        textField.setMessageText("User Name");
        table.add(textField).growX();
        
        table.row();
        textField = new TextField("", skin);
        textField.setMessageText("Password");
        textField.setPasswordCharacter('*');
        textField.setPasswordMode(true);
        table.add(textField).growX();
        
        table.row();
        Table subTable = new Table();
        table.add(subTable);
        
        subTable.defaults().minWidth(60);
        TextButton textButton = new TextButton("Login", skin);
        subTable.add(textButton);
        
        textButton = new TextButton("Quit", skin);
        subTable.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                window.addAction(Actions.sequence(Actions.fadeOut(.5f), Actions.removeActor()));
            }
        });
        
        table.row();
        Image image = new Image(skin, "split");
        table.add(image).growX();
        
        table.row();
        label = new Label("Server", skin, "subtitle");
        table.add(label).left();
        
        table.row();
        SelectBox<String> selectBox = new SelectBox<String>(skin);
        selectBox.setItems("West", "East", "The Very Far East");
        table.add(selectBox).padBottom(50);
        
        table.row();
        subTable = new Table();
        table.add(subTable).padBottom(20);
        
        subTable.defaults().left();
        CheckBox checkBox = new CheckBox("Log in as invisible", skin);
        subTable.add(checkBox);
        
        subTable.row();
        checkBox = new CheckBox("Log in automatically", skin);
        subTable.add(checkBox).padBottom(20);
        
        subTable.row();
        ButtonGroup<CheckBox> buttonGroup = new ButtonGroup<CheckBox>();
        checkBox = new CheckBox("Park", skin, "radio");
        buttonGroup.add(checkBox);
        subTable.add(checkBox);
        
        subTable.row();
        checkBox = new CheckBox("Playground", skin, "radio");
        buttonGroup.add(checkBox);
        subTable.add(checkBox);
        
        subTable.row();
        checkBox = new CheckBox("Sandbox", skin, "radio");
        buttonGroup.add(checkBox);
        subTable.add(checkBox);
        
        table.row();
        subTable = new Table();
        table.add(subTable);
        
        textField = new TextField("10", skin, "spinner");
        subTable.add(textField).width(70);
        
        Table buttonTable = new Table();
        subTable.add(buttonTable);
        
        Button button = new Button(skin, "spinner-up");
        buttonTable.add(button);
        
        buttonTable.row();
        button = new Button(skin, "spinner-down");
        buttonTable.add(button);
        
        table.row();
        final ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin, "curved");
        progressBar.setValue(50);
        table.add(progressBar).size(252, 5);
        
        table.row();
        final Slider slider = new Slider(0, 100, 1, false, skin, "curved");
        slider.setValue(50);
        table.add(slider).size(258, 9);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
            }
        });
        
        window.setColor(1, 1, 1, 0);
        stage.addActor(window);
        window.pack();
        window.setPosition(Math.round(x - window.getWidth()), Math.round(y - window.getHeight()));
        window.addAction(Actions.fadeIn(.5f));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
