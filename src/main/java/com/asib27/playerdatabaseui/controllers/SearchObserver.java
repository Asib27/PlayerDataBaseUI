/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

/**
 *
 * @author USER
 */
public interface SearchObserver<T> {
    public void update(DataProcessHelper<T> dataProcessor);
}
