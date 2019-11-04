import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProvisionercloudTestModule } from '../../../test.module';
import { NetKeyIndexDeleteDialogComponent } from 'app/entities/net-key-index/net-key-index-delete-dialog.component';
import { NetKeyIndexService } from 'app/entities/net-key-index/net-key-index.service';

describe('Component Tests', () => {
  describe('NetKeyIndex Management Delete Component', () => {
    let comp: NetKeyIndexDeleteDialogComponent;
    let fixture: ComponentFixture<NetKeyIndexDeleteDialogComponent>;
    let service: NetKeyIndexService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [NetKeyIndexDeleteDialogComponent]
      })
        .overrideTemplate(NetKeyIndexDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NetKeyIndexDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NetKeyIndexService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
