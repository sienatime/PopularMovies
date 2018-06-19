package net.emojiparty.android.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import java.util.List;

public class ListViewModel extends AndroidViewModel {
  private MutableLiveData<List<?>> list = new MutableLiveData<>();

  public ListViewModel(Application application) {
    super(application);
  }

  public void setList(List<?> list) {
    this.list.setValue(list);
  }

  public LiveData<List<?>> getList() {
    return list;
  }
}
