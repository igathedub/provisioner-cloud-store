import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { KeyIndexDetailComponent } from 'app/entities/key-index/key-index-detail.component';
import { KeyIndex } from 'app/shared/model/key-index.model';

describe('Component Tests', () => {
  describe('KeyIndex Management Detail Component', () => {
    let comp: KeyIndexDetailComponent;
    let fixture: ComponentFixture<KeyIndexDetailComponent>;
    const route = ({ data: of({ keyIndex: new KeyIndex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [KeyIndexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(KeyIndexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KeyIndexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.keyIndex).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
